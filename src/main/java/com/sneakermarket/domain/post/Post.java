package com.sneakermarket.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Post {

    private Long id;
    private String writer = "테스트";
    private String title;
    private String content;
    private int price;
    private int size;
    private boolean deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int viewCnt;
    private SaleStatus saleStatus;

    @Builder
    public Post(Long id, String title, String content, int price, int size, SaleStatus saleStatus) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.price = price;
        this.size = size;
        this.saleStatus = saleStatus;
    }

/*    public void updatePost(PostDto.Response postDto){
       this.title = postDto.getTitle();
       this.content = postDto.getContent();
       this.price = postDto.getPrice();
       this.size = postDto.getSize();
       this.saleStatus = postDto.getSaleStatus();
    }*/
}