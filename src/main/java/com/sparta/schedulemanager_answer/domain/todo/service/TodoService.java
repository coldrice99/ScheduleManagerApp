package com.sparta.schedulemanager_answer.domain.todo.service;

import com.sparta.schedulemanager_answer.domain.member.entity.Member;
import com.sparta.schedulemanager_answer.domain.member.repository.MemberRepository;
import com.sparta.schedulemanager_answer.domain.todo.dto.TodoMemberDto;
import com.sparta.schedulemanager_answer.domain.todo.dto.TodoRequestDto;
import com.sparta.schedulemanager_answer.domain.todo.dto.TodoResponseDto;
import com.sparta.schedulemanager_answer.domain.todo.entity.Todo;
import com.sparta.schedulemanager_answer.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        Todo todo = todoRepository.save(Todo.from(todoRequestDto)); // 리퀘스트 dto로 객체 생성 후 디비에 저장
        return todo.to();
    }

    public List<TodoResponseDto> getTodoList() {
        return todoRepository.findAll();
    }

    public List<TodoMemberDto> getTodoListWithPaging(int page, int size) {
        return todoRepository.findAllWithPaging(page, size);
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo =  todoRepository.findById(todoId);
        return todo.to(); // Todo -> ResponseDto
    }

    public void updateTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(todoId);
        if(todo == null){
            throw new IllegalArgumentException("해당 id를 찾을 수 없음");
        }

        /*if(!Objects.equals(todo.getPassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("패스워드가 틀립니다.");
        }*/

        Member member = memberRepository.findById(todo.getMemberId());

        if(!Objects.equals(member.getId(), requestDto.getMemberId())) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }

        todoRepository.update(todoId, requestDto);
    }


    public void deleteTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(todoId);
        if(todo == null){
            throw new IllegalArgumentException("해당 id를 찾을 수 없음");
        }

        /*if(!Objects.equals(todo.getPassword(), requestDto.getPassword())){
            throw new IllegalArgumentException("패스워드가 틀립니다.");
        }*/

        Member member = memberRepository.findById(todo.getMemberId());

        if(!Objects.equals(member.getId(), requestDto.getMemberId())) {
            throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
        }

        todoRepository.deleteById(todoId);
    }
}
