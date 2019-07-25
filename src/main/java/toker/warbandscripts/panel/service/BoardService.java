package toker.warbandscripts.panel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toker.warbandscripts.panel.entity.NoticeBoard;
import toker.warbandscripts.panel.repository.NoticeBoardRepository;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    public BoardService(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public List<NoticeBoard> getAllBoards() {
        return noticeBoardRepository.findAll();
    }
}
