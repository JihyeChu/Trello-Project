package com.sparta.trelloproject.column.controller;

import com.sparta.trelloproject.column.dto.ColumnMoveDto;
import com.sparta.trelloproject.column.dto.ColumnRequestDto;
import com.sparta.trelloproject.column.dto.ColumnResponseDto;
import com.sparta.trelloproject.column.service.ColumnService;
import com.sparta.trelloproject.common.api.ApiResponseDto;
import com.sparta.trelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class ColumnController {
    private final ColumnService columnService;

    // 컬럼 생성
    @PostMapping("/{boardId}/columns")
    public ResponseEntity<ApiResponseDto> createColumn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @PathVariable("boardId") Long boardId,
                                                       @RequestBody ColumnRequestDto requestDto) {
        columnService.createColumn(userDetails.getUser(), boardId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("컬럼 생성 완료", HttpStatus.CREATED.value()));
    }

    // 본인이 속한 보드의 컬럼 조회
    @GetMapping("/{boardId}/columns")
    public ResponseEntity<List<ColumnResponseDto>> getColumn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @PathVariable("boardId") Long boardId) {
        List<ColumnResponseDto> columnList = columnService.getColumn(userDetails.getUser(), boardId);
        return ResponseEntity.ok().body(columnList);
    }

    // 컬럼 이름 수정
    @PutMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<ApiResponseDto> updateColumn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody ColumnRequestDto requestDto,
                                                       @PathVariable("boardId") Long boardId,
                                                       @PathVariable("columnId") Long columnId) {
        columnService.updateColumn(userDetails.getUser(), requestDto, boardId, columnId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("컬럼 수정 완료", HttpStatus.OK.value()));
    }

    // 컬럼 삭제
    @DeleteMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<ApiResponseDto> deleteColumn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @PathVariable("boardId") Long boardId,
                                                       @PathVariable("columnId") Long columnId) {
        columnService.deleteColumn(userDetails.getUser(), boardId, columnId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("컬럼 삭제 완료", HttpStatus.OK.value()));
    }

    @PutMapping("/{boardId}/columns/order")
    public ResponseEntity<List<ColumnResponseDto>> moveColumn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable("boardId") Long boardId,
                                                              @RequestBody ColumnMoveDto moveDto) {
        List<ColumnResponseDto> results = columnService.moveColumn(userDetails.getUser(), boardId, moveDto);
        return ResponseEntity.ok().body(results);
    }
}