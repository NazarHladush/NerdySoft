package com.nerdysoft.mapper;

public interface GeneralMapper<M, D> {

     D convertToDto(M model)  ;

     M convertToModel(D dto) ;
}
