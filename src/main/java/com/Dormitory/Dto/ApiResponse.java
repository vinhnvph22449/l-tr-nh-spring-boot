package com.Dormitory.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
// khi 1 trường nào null thì sẽ ko in ra trường đấy nữa mà bỏ qua để code clear hơn
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{

    @Builder.Default
    private int code = 1000;
    private String messger;
    private T result;

}
