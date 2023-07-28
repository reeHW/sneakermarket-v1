package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.SearchDto;
import com.sneakermarket.common.paging.Pagination;
import com.sneakermarket.common.paging.PagingResponse;
import com.sneakermarket.config.SessionConstants;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

import static com.sneakermarket.domain.post.PostDto.Response.PostListToDto;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    /**
     * 게시글 저장
     * @param editForm
     * @return Generated PK
     */

    @Transactional
    public Long savePost(final HttpSession session, final PostDto.EditForm editForm) {

        MemberDto.FindForm loginMember = (MemberDto.FindForm) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        Post post = Post.builder()
                .id(editForm.getId())
                .writer(loginMember.getNickname())
                .userId(loginMember.getId())
                .title(editForm.getTitle())
                .saleStatus(editForm.getSaleStatus())
                .content(editForm.getContent())
                .price(editForm.getPrice())
                .size(editForm.getSize())
                .build();
        postMapper.save(post);
        return post.getId();
    }

    /**
     * 게시글 수정
     * @param editForm - 게시글 정보
     * @return PK
     */
    @Transactional
    public Long updatePost(final PostDto.EditForm editForm){
        Post post = Post.builder()
                .id(editForm.getId())
                .title(editForm.getTitle())
                .saleStatus(editForm.getSaleStatus())
                .content(editForm.getContent())
                .price(editForm.getPrice())
                .size(editForm.getSize())
                .build();

        postMapper.update(post);
        return post.getId();
    }

    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deletePost(final Long id){
        postMapper.deleteById(id);
        return id;
    }

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostDto.Response findPostById(final Long id) {
        Post post = postMapper.findById(id);
        return new PostDto.Response(post);
    }

    /**
     * 게시글 리스트 조회
     * @param params - search conditions
     * @return list & pagination information
     */
    public PagingResponse<PostDto.Response> findAllPost(final SearchDto params){

        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = postMapper.count(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        //params.setPagination(pagination);


        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<Post> entity = postMapper.findAll(params);
        List<PostDto.Response> list = PostListToDto(entity);
        return new PagingResponse<>(list, pagination);

    }

}
