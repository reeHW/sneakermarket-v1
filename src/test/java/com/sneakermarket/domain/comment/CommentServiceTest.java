package com.sneakermarket.domain.comment;

import com.sneakermarket.common.paging.Pagination;
import com.sneakermarket.common.paging.PagingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.sneakermarket.domain.comment.CommentDto.CommentListToDto;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentMapper commentMapper;

/*    @Test
    void saveByForeach() {
        for (int i = 1; i <= 100; i++) {
            CommentDto.EditForm commentDto = new CommentDto.EditForm();
            commentDto.setPostId(999L);
            commentDto.setWriter("테스터" + i);
            commentDto.setContent("테스트" + i);

            commentService.saveComment(commentDto);

        }
    }*/

    @Test
    void findAllCommentTest(){

        CommentSearchDto params = new CommentSearchDto();
        int count = commentMapper.count(params);

        Pagination pagination = new Pagination(count, params);
        List<Comment> entity = commentMapper.findAll(params);
        List<CommentDto.Response> list = CommentListToDto(entity);
        PagingResponse<CommentDto.Response> response = new PagingResponse<>(list, pagination);

        System.out.println(response.getList());
    }
}
