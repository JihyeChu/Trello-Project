package com.sparta.trelloproject.column.entity;

import com.sparta.trelloproject.board.entity.BoardEntity;
import com.sparta.trelloproject.card.entity.CardEntity;
import com.sparta.trelloproject.column.dto.ColumnRequestDto;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "column_tb")
public class ColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String columnName;

    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "column", cascade = CascadeType.REMOVE)
    private List<CardEntity> cardList = new ArrayList<>();

    public ColumnEntity(String columnName, BoardEntity board, User user, int position) {
        this.columnName = columnName;
        this.board = board;
        this.user = user;
        this.position = position;
    }

    public void update(ColumnRequestDto requestDto) {
        this.columnName = requestDto.getColumnName();
    }

    public void moveColumn(int position) {
        this.position = position;
    }
}
