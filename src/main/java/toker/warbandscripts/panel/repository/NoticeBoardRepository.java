package toker.warbandscripts.panel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import toker.warbandscripts.panel.entity.NoticeBoard;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer>, JpaSpecificationExecutor<NoticeBoard> {
}
