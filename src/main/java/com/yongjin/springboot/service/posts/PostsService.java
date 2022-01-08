package com.yongjin.springboot.service.posts;

import com.yongjin.springboot.controller.dto.PostListResponseDto;
import com.yongjin.springboot.controller.dto.PostsResponseDto;
import com.yongjin.springboot.controller.dto.PostsSaveRequestDto;
import com.yongjin.springboot.controller.dto.PostsUpdateRequestDto;
import com.yongjin.springboot.domain.posts.Posts;
import com.yongjin.springboot.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    
    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
            .map(PostListResponseDto::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public PostsResponseDto findById(Long id)  {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id는 존재하지 않습니다."));

        return new PostsResponseDto(posts);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id는 존재하지 않습니다."));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

}
