package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//에러 메세지를 호출 할 class를 생성해보겠습니다.
@Getter
@Setter
@NoArgsConstructor
public class Message {
    String message = "";
    String href = "";

    public Message(String message, String href) {
        this.message = message;
        this.href = href;
    }
}
