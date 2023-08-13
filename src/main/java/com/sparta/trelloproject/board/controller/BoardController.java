package com.sparta.trelloproject.board.controller;

import com.sparta.trelloproject.board.dto.BoardListResponseDto;
import com.sparta.trelloproject.board.dto.BoardRequestDto;
import com.sparta.trelloproject.board.dto.BoardResponseDto;
import com.sparta.trelloproject.board.service.BoardService;
import com.sparta.trelloproject.common.api.ApiResponseDto;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 보드 생성
    @PostMapping("/boards")
    public ResponseEntity<ApiResponseDto> createBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BoardRequestDto boardRequestDto) throws IOException {

        boardService.createBoard(userDetails, boardRequestDto);

        return ResponseEntity.ok().body(new ApiResponseDto("보드 생성 성공", HttpStatus.OK.value()));
    }

    // 로그인사용자가 속한 보드 전체 조회 (본인생성 + 콜라보레이트권한있는 보드)
    @GetMapping("/boards")
    public List<BoardListResponseDto> getBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return boardService.getBoards(userDetails.getUser());
    }

    // 보드 단건 조회
    @GetMapping("/boards/{boardId}")
    public BoardResponseDto getBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId) {

        return boardService.getBoard(userDetails.getUser(), boardId);
    }

    // 보드 수정
    @PutMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponseDto> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId,
            @RequestBody BoardRequestDto boardRequestDto) throws IOException {

        boardService.updateBoard(userDetails, boardId, boardRequestDto);

        return ResponseEntity.ok().body(new ApiResponseDto("보드 수정 성공", HttpStatus.OK.value()));
    }

    // 보드 삭제
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponseDto> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId) {

        boardService.deleteBoard(userDetails, boardId);

        return ResponseEntity.ok().body(new ApiResponseDto("보드 삭제 성공", HttpStatus.OK.value()));
    }

    // 보드 콜라보레이터 추가
    @PostMapping("/boards/{boardId}/invite/{userId}")
    public ResponseEntity<ApiResponseDto> inviteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId,
            @PathVariable Long userId) {

        boardService.inviteUser(userDetails, boardId, userId);

        return ResponseEntity.ok().body(new ApiResponseDto("콜라보레이터 추가 성공", HttpStatus.OK.value()));
    }
}
