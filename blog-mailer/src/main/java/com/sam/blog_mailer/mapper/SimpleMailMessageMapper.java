package com.sam.blog_mailer.mapper;

import com.sam.blog_mailer.dto.request.MailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.mail.SimpleMailMessage;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SimpleMailMessageMapper {

}
