package toker.panel.controller.gameapi;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toker.panel.entity.*;
import toker.panel.entity.pk.FactionPK;
import toker.panel.repository.*;
import toker.panel.service.PlayerService;
import toker.panel.util.Languages;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@Transactional
public class PlayerGameController {

    private PlayerService playerService;

    private ItemRepository itemRepository;
    private TroopRepository troopRepository;
    private FactionRepository factionRepo;
    private IpRecordRepository ipRecordRepository;
    private LanguageRepository languageRepo;
    private LanguageProficiencyRepository proficiencyRepo;

    public PlayerGameController(PlayerService playerService,
                                ItemRepository itemRepository,
                                TroopRepository troopRepository,
                                FactionRepository factionRepo,
                                IpRecordRepository ipRecordRepository,
                                LanguageRepository languageRepo,
                                LanguageProficiencyRepository proficiencyRepo) {
        this.playerService = playerService;
        this.itemRepository = itemRepository;
        this.troopRepository = troopRepository;
        this.factionRepo = factionRepo;
        this.ipRecordRepository = ipRecordRepository;
        this.languageRepo = languageRepo;
        this.proficiencyRepo = proficiencyRepo;
    }

    @GetMapping("/gameapi/saveplayer")
    public void save(
            int uniqueid, String name, String scenename,
            int gold, int hp, int food, int fatigue,
            int posx, int posy, int posz,
            int horse, int horseslot,
            int factionid, int troopid,
            int headarmor, int bodyarmor,
            int footarmor, int glovesarmor,
            int item0, int item1, int item2, int item3,
            int ammo0, int ammo1, int ammo2, int ammo3
    ) {
        Server server = (Server) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        playerService.getPlayer(name, server.getId(), uniqueid).ifPresent(player -> {
            player.setSceneName(scenename);
            player.setGold(gold);
            player.setHp(hp);
            player.setFood(food);
            player.setFatigue(fatigue);
            player.setPosX(posx);
            player.setPosY(posy);
            player.setPosZ(posz);
            player.setHorse(itemRepository.getOne(horse));
            player.setHorseSlot(horseslot);
            player.setFaction(factionRepo.getOne(new FactionPK(factionid, server.getId())));
            player.setTroop(troopRepository.getOne(troopid));
            player.setHeadArmor(itemRepository.getOne(headarmor));
            player.setBodyArmor(itemRepository.getOne(bodyarmor));
            player.setHandArmor(itemRepository.getOne(glovesarmor));
            player.setFootArmor(itemRepository.getOne(footarmor));
            player.setItem_0(itemRepository.getOne(item0));
            player.setItem_1(itemRepository.getOne(item1));
            player.setItem_2(itemRepository.getOne(item2));
            player.setItem_3(itemRepository.getOne(item3));
            player.setAmmo_0(ammo0);
            player.setAmmo_1(ammo1);
            player.setAmmo_2(ammo2);
            player.setAmmo_3(ammo3);

            player = playerService.savePlayer(player);

            Timestamp now = Timestamp.from(Instant.now());
            if (player.getLastLogTime() != null && player.isWounded() && player.getLastLogTime().before(now)) {
                long diffMilli = now.getTime() - player.getLastLogTime().getTime();
                playerService.serveWoundTime(player.getId(), (int)diffMilli);
            }
        });
    }

    @GetMapping("/gameapi/loadplayer")
    public String load(int playerid, int uniqueid, String name) throws ChangeSetPersister.NotFoundException {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(name, server.getId()).orElse(null);

        if (player == null) {
            player = new Player();

            player.setName(name);
            player.setUniqueId(uniqueid);
            player.setFaction(factionRepo.getOne(new FactionPK(0, server.getId())));
            player.setTroop(troopRepository.getOne(14));
            player.setLastLogTime(Timestamp.from(Instant.now()));
            playerService.spawnStartingGear(player, server.getId());
            playerService.savePlayer(player);

            LanguageProficiency proficiency = new LanguageProficiency();
            proficiency.setPlayer(player);
            proficiency.setLanguage(languageRepo.getOne(Languages.DEFAULT.getId()));
            proficiencyRepo.save(proficiency);

            player = playerService.getPlayer(name, server.getId(), uniqueid)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
        }
        else {
            player.setLastLogTime(Timestamp.from(Instant.now()));
            playerService.savePlayer(player);
        }

        Player finalPlayer = player;

        List<Integer> languageIds = proficiencyRepo.findAll((root, query, builder) ->
                builder.equal(root.get(LanguageProficiency_.player).get(Player_.id), finalPlayer.getId()))
                .stream()
                .map(languageProficiency -> languageProficiency.getLanguage().getId())
                .collect(Collectors.toList());

        return String.format("%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|" +
                "%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%s|%d|%d|%d|%d|%d|%d|%d",
                playerid, player.getUniqueId(), player.getGold(),
                player.getHp(), player.getFood(),
                player.getPosX(), player.getPosY(), player.getPosZ(),
                player.getFaction().getIndex(), player.getTroop().getId(),
                player.getHeadArmor().getId(), player.getBodyArmor().getId(),
                player.getHandArmor().getId(), player.getFootArmor().getId(),
                player.getItem_0().getId(), player.getItem_1().getId(),
                player.getItem_2().getId(), player.getItem_3().getId(),
                player.getAmmo_0(), player.getAmmo_1(),
                player.getAmmo_2(), player.getAmmo_3(),
                player.isWounded() ? 1 : 0,
                player.getFatigue(), player.getRidingTier(),
                player.getHorse().getId(), player.getHorseSlot(),
                0, player.getSceneName(),
                languageIds.contains(Languages.COMMON.getId()) ? 1 : 0,
                languageIds.contains(Languages.VAEGIR.getId()) ? 1 : 0,
                languageIds.contains(Languages.NORDIC.getId()) ? 1 : 0,
                languageIds.contains(Languages.SARRANID.getId()) ? 1 : 0,
                languageIds.contains(Languages.KHERGIT.getId()) ? 1 : 0,
                languageIds.contains(Languages.GEROIAN.getId()) ? 1 : 0,
                languageIds.contains(Languages.BALIONESE.getId()) ? 1 : 0);
    }

    @GetMapping("/gameapi/woundplayer")
    public void wound(String name, int uniqueid) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        playerService.getPlayer(name, server.getId(), uniqueid)
                .ifPresent(player -> playerService.woundPlayer(player));
    }

    @GetMapping("/gameapi/healplayer")
    public String heal(String name, String patientname,
                     int playerid, int patientplayerid) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player doctor = playerService.getPlayer(name, server.getId()).orElse(null);
        Player patient = playerService.getPlayer(patientname, server.getId()).orElse(null);

        boolean treated = false;
        if (doctor != null && patient != null) {
            treated = playerService.treatPlayer(doctor, patient);
        }

        return String.format("%d|%d|%d", playerid, patientplayerid, treated ? 1 : 0);
    }

    @GetMapping("/gameapi/checkwound")
    public String checkWound(int playerid, String name)
            throws ChangeSetPersister.NotFoundException {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(name, server.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (player.getWoundTime() == null) {
            return String.format("%d|%d", playerid, 0);
        }

        if (player.getTreatmentTime() == null ||
                player.getTreatmentTime().before(player.getWoundTime())) {
            boolean wounded = TimeUnit.MILLISECONDS.toMinutes(player.getServedWoundTime()) < player.getWoundDuration() * 60;
            return String.format("%d|%d", playerid, wounded ? 1 : 0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(player.getTreatmentTime());
        calendar.add(Calendar.HOUR, player.getWoundDuration());
        boolean wounded = calendar.toInstant().isAfter(Instant.now());
        return String.format("%d|%d", playerid, wounded ? 1 : 0);
    }

    @GetMapping("/gameapi/checkwoundduration")
    public String checkWoundDuration(int playerid, String name)
            throws ChangeSetPersister.NotFoundException {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(name, server.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (player.getWoundTime() == null) {
            return String.format("%d|0", playerid);
        }

        if (player.getTreatmentTime() == null ||
                player.getTreatmentTime().before(player.getWoundTime())) {
            boolean wounded = TimeUnit.MILLISECONDS.toMinutes(player.getServedWoundTime()) < player.getWoundDuration() * 60;
            if (wounded) {
                return String.format("%d|1|%d", playerid,
                        player.getWoundDuration() * 60 - TimeUnit.MILLISECONDS.toMinutes(player.getServedWoundTime()));
            }
            return String.format("%d|0", playerid);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(player.getTreatmentTime());
        calendar.add(Calendar.HOUR, player.getWoundDuration());
        boolean wounded = calendar.toInstant().isAfter(Instant.now());

        if (wounded) {
            long diffMilli = Timestamp.from(calendar.toInstant()).getTime()
                    - Timestamp.from(Instant.now()).getTime();
            long diffMins = TimeUnit.MINUTES.convert(diffMilli, TimeUnit.MILLISECONDS);
            return String.format("%d|1|%d", playerid, (int)diffMins);
        }

        return String.format("%d|0", playerid);
    }

    @GetMapping("/gameapi/getadminperms")
    public String adminPerms(int playerid, int uniqueid) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        AdminPermissions ap =
                playerService.getAdminPermissions(server.getId(), uniqueid).orElse(null);

        if (ap != null) {
            return String.format("%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|" +
                            "%d|%d|%d|%d|%d|%d|%d|%d|%d|%d|%d",
                    playerid, uniqueid, 1, ap.getPanel() ? 1 : 0,
                    ap.getGold() ? 1 : 0, ap.getKick() ? 1 : 0, ap.getTemporaryBan() ? 1 : 0,
                    ap.getPermanentBan() ? 1 : 0, ap.getKillFade() ? 1 : 0, ap.getFreeze() ? 1 : 0,
                    ap.getTeleportSelf() ? 1 : 0, ap.getAdminItems() ? 1 : 0, ap.getHealSelf() ? 1 : 0,
                    ap.getGodlikeTroop() ? 1 : 0, ap.getShips() ? 1 : 0, ap.getAnnounce() ? 1 : 0,
                    ap.getOverridePoll() ? 1 : 0, ap.getAllItems() ? 1 : 0, ap.getMute() ? 1 : 0,
                    ap.getAnimals() ? 1 : 0, ap.getJoinFactions() ? 1 : 0, ap.getFactions() ? 1 : 0);
        }
        return String.format("%d|%d|%d", playerid, uniqueid, 0);
    }

    @GetMapping("/gameapi/inventoryget")
    public String getInventory(String playername, int playerid) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(playername, server.getId()).orElse(null);

        if (player != null) {
            Inventory inventory = playerService.getPlayerInventory(player.getId());
            if (inventory == null) {
                playerService.createInventory(player);
                inventory = playerService.getPlayerInventory(player.getId());
            }
            Collection<InventorySlot> slots = inventory.getSlots();
            StringBuilder sb = new StringBuilder(String.format("%d|%d|%s",
                    playerid, slots.size(), player.getName()));

            slots.stream()
                    .sorted(Comparator.comparingInt(InventorySlot::getSlot))
                    .forEach(inventorySlot -> {
                sb.append('|');
                sb.append(inventorySlot.getItem().getId());
            });

            slots.stream()
                    .sorted(Comparator.comparingInt(InventorySlot::getSlot))
                    .forEach(inventorySlot -> {
                sb.append('|');
                sb.append(inventorySlot.getAmmo());
            });

            return sb.toString();
        }

        return "0";
    }

    @GetMapping("/gameapi/inventoryput")
    public void inventoryPut(String playername, int slot, int item, int ammo) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        int finalSlot = slot + 1;
        playerService.getPlayer(playername, server.getId()).ifPresent(player -> {
            playerService.updateInventorySlot(
                    playerService.getPlayerInventory(player.getId()).getId(),
                    finalSlot, item, ammo);
        });
    }

    @GetMapping("/gameapi/getmount")
    public String getMount(String name, int playerid, int th) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(name, server.getId()).orElse(null);

        if (player != null) {
            int horse = switch (th) {
                case 1 -> player.getHorse_0().getId();
                case 2 -> player.getHorse_1().getId();
                case 3 -> player.getHorse_2().getId();
                default -> 0;
            };

            int stable = switch (th) {
                case 1 -> player.getHorse_1_stableId();
                case 2 -> player.getHorse_2_stableId();
                case 3 -> player.getHorse_3_stableId();
                default -> 0;
            };

            return String.format("%d|%d|%d|%d|%d", playerid,
                    horse, 0, th, stable);
        }

        return String.format("%d|%d", playerid, 0);
    }

    @GetMapping("/gameapi/puthorseinstable")
    public void parkHorse(String name, int th, int stableid) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(name, server.getId()).orElse(null);

        if (player != null) {
            switch (th) {
                case 1 -> player.setHorse_1_stableId(stableid);
                case 2 -> player.setHorse_2_stableId(stableid);
                case 3 -> player.setHorse_3_stableId(stableid);
            }
            playerService.savePlayer(player);
        }
    }

    @GetMapping("/gameapi/iptrack")
    public void ipTrack(int uniqueid, String ip) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        IpRecord record = new IpRecord();
        record.setServer(server);
        record.setIpAddress(ip);
        record.setUniqueId(uniqueid);
        ipRecordRepository.save(record);
    }

    @GetMapping("/gameapi/getdescription")
    public String getDescription(String name, int playerid, int th) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(name, server.getId()).orElse(null);

        if (player != null) {
            String description = switch (th) {
                case 1 -> player.getDescription1();
                case 2 -> player.getDescription2();
                case 3 -> player.getDescription3();
                default -> "<Wrong slot>";
            };
            return String.format("%d|%s", playerid, description);
        }

        return String.format("%d|<Wrong slot>", playerid);
    }

    @GetMapping("/gameapi/inspectdesc")
    public String inspectDescription(String name, int playerid, int th) {
        return getDescription(name, playerid, th);
    }

    @GetMapping("/gameapi/setdescription")
    public String setDescription(String name, String description,
                                 int playerid, int th) {
        Server server = (Server)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Player player = playerService.getPlayer(name, server.getId()).orElse(null);

        if (player != null) {
            switch (th) {
                case 1 -> player.setDescription1(description);
                case 2 -> player.setDescription2(description);
                case 3 -> player.setDescription3(description);
            };
            playerService.savePlayer(player);
            return String.format("%d|1", playerid);
        }

        return String.format("%d|0", playerid);
    }
}
