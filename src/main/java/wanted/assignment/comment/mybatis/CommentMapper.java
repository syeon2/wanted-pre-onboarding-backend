package wanted.assignment.comment.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import wanted.assignment.comment.dao.Comment;
import wanted.assignment.common.basewrapper.BaseEntity;

@Mapper
public interface CommentMapper {

	void save(BaseEntity<Comment> createCommentDto);

	List<Comment> findByPostId(Long id);

	void update(Long postId, BaseEntity<Comment> updateCommentDto);

	void delete(Long postId);
}
