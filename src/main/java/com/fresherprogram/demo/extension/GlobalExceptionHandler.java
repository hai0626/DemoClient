package com.fresherprogram.demo.extension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fresherprogram.demo.repository.MessageRepository;

import net.bytebuddy.asm.Advice.Return;

@Component
public class GlobalExceptionHandler {

	@Autowired
	private MessageRepository messageRepository;
	
	public String clientExist() {
		return messageRepository.findMessageById(3);
	}
	
	public String dayLarger() {
		return messageRepository.findMessageById(4);
	}
	
	public String regExist() {
		return messageRepository.findMessageById(5);
	}
	
	public String duplicate() {
		return messageRepository.findMessageById(8);
	}
	
	public String policyExist() {
		return messageRepository.findMessageById(9);
	}
	
	public String policyStatus() {
		return messageRepository.findMessageById(10);
	}
	
	public String larger() {
		return messageRepository.findMessageById(11);
	}
	
	public String dayFuture() {
		return messageRepository.findMessageById(12);
	}
	
}
