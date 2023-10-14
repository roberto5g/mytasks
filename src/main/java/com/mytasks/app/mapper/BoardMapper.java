package com.mytasks.app.mapper;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.model.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {TaskMapper.class, UserMapper.class})
public interface BoardMapper {

    BoardMapper boardMapperInstance = Mappers.getMapper(BoardMapper.class);

    BoardResponse toBoardResponse(Board board);

    BoardResponseDetails toBoardResponseDetails(Board board);

    Board boardRequestToBoard(BoardRequest boardRequest);

    Board boardResponseToBoardId(BoardResponse boardResponse);

    default List<BoardResponse> toBoardResponseList(List<Board> boards){
        List<BoardResponse> list = new ArrayList<>();
        boards.forEach(d-> list.add(toBoardResponse(d)));
        return list;
    }

}

