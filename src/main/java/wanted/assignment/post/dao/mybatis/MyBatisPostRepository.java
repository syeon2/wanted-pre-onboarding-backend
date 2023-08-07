package wanted.assignment.post.dao.mybatis;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import wanted.assignment.common.basewrapper.BaseEntity;
import wanted.assignment.post.dao.PostRepository;
import wanted.assignment.post.dao.domain.PostDetail;
import wanted.assignment.post.dao.domain.PostSimple;

@Repository
@RequiredArgsConstructor
public class MyBatisPostRepository implements PostRepository {

	private static final Integer PAGE_OFFSET = 10;
	private final PostMapper postMapper;

	@Override
	public Long save(PostDetail post) {
		BaseEntity<PostDetail> createData = BaseEntity.createData(post);
		postMapper.save(createData);

		return createData.getData().getId();
	}

	@Override
	public PostDetail findById(Long id) {
		return postMapper.findById(id)
			.orElseThrow(() -> new NoSuchElementException("아이디에 해당하는 게시글이 존재하지 않습니다."));
	}

	@Override
	public List<PostSimple> findAll(Long id) {
		return postMapper.findAll(id, PAGE_OFFSET);
	}

	@Override
	public Long update(Long id, PostDetail post) {
		BaseEntity<PostDetail> updateData = BaseEntity.updateData(post);
		postMapper.update(id, updateData);

		return id;
	}

	@Override
	public void delete(Long id) {
		postMapper.delete(id);
	}
}
