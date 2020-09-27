package toker.panel.repository;

import toker.panel.entity.DownloadToken;

import java.util.Optional;

public interface DownloadTokenRepository extends BaseRepository<DownloadToken, Integer> {
    Optional<DownloadToken> findByToken(String token);
}
