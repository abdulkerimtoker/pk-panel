package toker.warbandscripts.panel.repository;

import toker.warbandscripts.panel.entity.DownloadToken;

import java.util.Optional;

public interface DownloadTokenRepository extends BaseRepository<DownloadToken, Integer> {
    Optional<DownloadToken> findByToken(String token);
}
