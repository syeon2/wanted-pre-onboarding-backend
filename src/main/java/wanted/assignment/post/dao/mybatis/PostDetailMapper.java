package wanted.assignment.post.dao.mybatis;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import wanted.assignment.common.basewrapper.BaseEntity;
import wanted.assignment.post.dao.domain.PostDetail;

@Mapper
public interface PostDetailMapper {

	Long save(BaseEntity<PostDetail> postDetail);

	Optional<PostDetail> findById(Long postId);

	Long update(@Param("postId") Long postId, @Param("updateParam") BaseEntity<PostDetail> postDetail);

	void delete(Long postId);
}
