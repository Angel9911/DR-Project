package com.example.demo.utils;

import com.example.demo.models.annotations.Phone;
import com.example.demo.models.validators.PhoneValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class ObjectMapper {
    private static ModelMapper mapper;
    private static TypeMap typeMap;

    private ObjectMapper(){

    }

    public static ModelMapper getMapperInstance(){

        if(mapper == null){

            mapper = new ModelMapper();
        }
        return mapper;
    }

    public static<S,D> TypeMap<S,D> getTypeMapInstance(Class<S> source, Class<D> destination){
        if(mapper!=null){
            typeMap = mapper.createTypeMap(source,destination);
        }
        return typeMap;
    }

    public static <S,D> D map(S source, Class<D> destination){
        return mapper.map(source,destination);
    }

    public static <S,D> List<D> map(List<S> entities, Class<D> destination){
        return entities.stream()
                .map(entity -> mapper.map(entity,destination))
                .collect(Collectors.toList());
    }

    public static <S,D> List<D> map(List<S> entities, Class<D> destination, boolean filter){
        if(filter) {
            return entities.stream()
                    .filter(entity -> {
                        try {
                            Phone phone = entity.getClass().getMethod("phone").getAnnotation(Phone.class);
                            return phone != null && phone.toString() != null;
                        } catch (NoSuchMethodException noSuchMethodException) {
                            noSuchMethodException.printStackTrace();
                            return false;
                        }
                    })
                    .map(entity -> mapper.map(entity, destination))
                    .collect(Collectors.toList());
        }
       return map(entities,destination);
    }

    public static <S,D> List<D> map(List<S> entities, TypeMap<S,D> typeMap){

        return entities.stream()
                    .map(typeMap::map)
                    .collect(Collectors.toList());

    }

}
