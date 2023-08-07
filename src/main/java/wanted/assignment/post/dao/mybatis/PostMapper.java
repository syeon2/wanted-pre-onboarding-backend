package wanted.assignment.post.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import wanted.assignment.common.basewrapper.BaseEntity;
import wanted.assignment.post.dao.domain.PostDetail;
import wanted.assignment.post.dao.domain.PostSimple;

@Mapper
public interface PostMapper {

	void save(BaseEntity<PostDetail> post);

	Optional<PostDetail> findById(Long id);

	List<PostSimple> findAll(Long id, Integer limit);

	void update(@Param("id") Long id, @Param("updateParam") BaseEntity<PostDetail> post);

	void delete(Long id);
}
