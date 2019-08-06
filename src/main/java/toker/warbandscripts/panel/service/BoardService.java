package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.NoticeBoard;
import toker.warbandscripts.panel.entity.NoticeBoardAccess;
import toker.warbandscripts.panel.repository.NoticeBoadAccessRepository;
import toker.warbandscripts.panel.repository.NoticeBoardRepository;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private NoticeBoadAccessRepository noticeBoadAccessRepository;

    public BoardService(NoticeBoardRepository noticeBoardRepository,
                        NoticeBoadAccessRepository noticeBoadAccessRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoadAccessRepository = noticeBoadAccessRepository;
    }

    public List<NoticeBoard> getAllBoards() {
        return noticeBoardRepository.findAll();
    }

    public NoticeBoardAccess saveBoardAccess(NoticeBoardAccess boardAccess) {
        return noticeBoadAccessRepository.saveAndFlush(boardAccess);
    }
}
