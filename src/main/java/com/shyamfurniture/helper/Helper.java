package com.shyamfurniture.helper;

import com.shyamfurniture.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type){
          List<U> entity= page.getContent();

           List<V> dtoList= entity.stream().map(object->new ModelMapper().map(object,type)).toList();
           PageableResponse<V> response=new PageableResponse<>();
           response.setContent(dtoList);
           response.setPageNumber(page.getNumber());
           response.setPageSize(page.getSize());
           response.setTotalElement(page.getTotalElements());
           response.setTotalPage(page.getTotalPages());
           response.setLastPage(page.isLast());
           return response;

    }
}
