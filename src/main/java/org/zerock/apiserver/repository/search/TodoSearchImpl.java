package org.zerock.apiserver.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.apiserver.domain.QTodo;
import org.zerock.apiserver.domain.Todo;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;

import java.util.List;
import java.util.Objects;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {

        log.info("search1...............");

        QTodo todo = QTodo.todo; // 쿼리를 실행하기 위한 객체
        JPQLQuery<Todo> query = from(todo);
        //query.where(todo.title.contains("1"));

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("tno").descending());

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query);

        List<Todo> list =  query.fetch();

        long total = query.fetchCount();


        return new PageImpl<>(list, pageable, total);
    }
}
