package com.sparta.trelloproject.column.entity;

import com.sparta.trelloproject.board.entity.Board;
import com.sparta.trelloproject.column.dto.ColumnRequestDto;
import com.sparta.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "column")
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.persistence.Column
    private String columnName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
//    private Board board;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

//    @OneToMany(mappedBy = "column", cascade = CascadeType.REMOVE)
//    private List<Card> cardList = new ArrayList<>();

    public Column(String columnName, Board board, User user) {
        this.columnName = columnName;
//        this.board = board;
//        this.user = user;
    }

    public void update(ColumnRequestDto requestDto) {
        this.columnName = requestDto.getColumnName();
    }
}
