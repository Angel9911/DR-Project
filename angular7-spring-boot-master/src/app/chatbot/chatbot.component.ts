import {Component, OnInit} from '@angular/core';
import {ChatbotServiceService} from '../service/chatbot-service.service';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent implements OnInit {
  showChatbot: boolean;
  messages: any[] = [];
  userInput: string;
  response: string;
  sessionId: string;

  constructor(private chatbotService: ChatbotServiceService) {
    this.showChatbot = true;
  }

  ngOnInit(): void {
    console.log('test');
    this.chatbotService.getSessionId().subscribe(res => {
      console.log(res);
      this.sessionId = res;
    });
    // const d = JSON.stringify(this.chatbotService.getSessionId());
    // console.log(d);
  }

  openChatbot() {
    this.showChatbot = true;
  }

  closeChatbot() {
    this.showChatbot = false;
    this.messages = [];
    this.userInput = '';
  }

  sendMessage() {
    if (this.userInput.trim() === '') {
      return;
    }

    this.messages.push({ text: this.userInput, isUserMessage: true });
    // this.messages.push({ text: this.userInput, isUserMessage: true });

    // Send user input to Spring Boot backend
    // @ts-ignore
    this.chatbotService.sendMessage(this.userInput, this.sessionId).subscribe(res => {
      const message = res.message;
      this.messages.push({ text: message, isUserMessage: false });
    },
      error => {
        console.error('Error:', error);
        // Display a user-friendly error message
        // or handle the error in some other way
      });
  }

}
