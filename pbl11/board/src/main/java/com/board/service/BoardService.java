package com.board.service;

import com.board.domain.entity.Board;
import com.board.domain.entity.BoardStatus;
import com.board.domain.entity.Comment;
import com.board.domain.response.BoardResponse;
import com.board.repository.BoardRepository;
import com.board.repository.BoardRepositoryCustom;
import com.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardRepositoryCustom boardRepositoryCustom;


    public List<BoardResponse> searchBoardList(int page, int pageSize, Sort.Direction direction) {
        return boardRepository.findAll(
                PageRequest.of(page, pageSize, Sort.by(direction, "boardNo"))
        ).map(BoardResponse::from).toList();
    }

    public BoardResponse getBoard(Long boardNo) {
        return boardRepository.findById(boardNo)
                .map(BoardResponse::from)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    public BoardResponse writeBoard(String title, String body) {
        Board board = new Board();
        board.setTitle(title);
        board.setBody(body);
        board.setBoardStatus(BoardStatus.ACTIVE);
        return BoardResponse.from(boardRepository.save(board));
    }

    public BoardResponse editBoard(Long boardNo, String body) {
        return boardRepository.findById(boardNo)
                .map(board -> {
                    board.setBody(body);
                    return board;
                }).map(BoardResponse::from)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }

    public Long deleteBoard(Long boardNo) {
        return boardRepositoryCustom.find(boardNo)
                .map(board -> {
                    commentRepository.deleteCommentList(board.getComments().stream().map(Comment::getCommentNo).collect(Collectors.toList()));
                    boardRepository.delete(board);
                    return board.getBoardNo();
                })
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
    }
}
