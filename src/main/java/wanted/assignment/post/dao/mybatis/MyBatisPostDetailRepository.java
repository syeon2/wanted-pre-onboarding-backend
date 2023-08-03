package wanted.assignment.post.dao.mybatis;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.basewrapper.BaseEntity;
import wanted.assignment.post.dao.PostDetailRepository;
import wanted.assignment.post.dao.domain.PostDetail;

@Repository
@RequiredArgsConstructor
public class MyBatisPostDetailRepository implements PostDetailRepository {

	private final PostDetailMapper postDetailMapper;

	@Override
	public Long save(PostDetail postDetail) {
		BaseEntity<PostDetail> createData = BaseEntity.createData(postDetail);
		postDetailMapper.save(createData);

		return createData.getData().getPostId();
	}

	@Override
	public PostDetail findById(Long postId) {
		return postDetailMapper.findById(postId)
			.orElseThrow(() -> new NoSuchElementException("게시글에 해당하는 내용이 존재하지 않습니다."));
	}

	@Override
	public Long update(Long postId, PostDetail postDetail) {
		BaseEntity<PostDetail> updateData = BaseEntity.updateData(postDetail);
		postDetailMapper.update(postId, updateData);

		return postId;
	}

	@Override
	public void delete(Long postId) {
		postDetailMapper.delete(postId);
	}
}
