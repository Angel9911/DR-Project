import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const chatbotUrl = 'http://localhost:8082/chatbot/dialogflow/';
const reqHeader = new HttpHeaders({
  'Content-Type': 'application/json',
  Authentication: 'Bearer ' + JSON.parse(localStorage.getItem('token'))
});

@Injectable({
  providedIn: 'root'
})
export class ChatbotServiceService {

  constructor(private httpChatBot: HttpClient) { }

  getSessionId() {
    const reqHeader2 = new HttpHeaders({
    'Content-Type': 'application/text',
      Authentication: 'Bearer ' + JSON.parse(localStorage.getItem('token'))
  });
    const url = chatbotUrl + 'sessionId';
    // @ts-ignore
    return this.httpChatBot.get<any>(url, {responseType: 'text', headers: reqHeader2});
  }
  sendMessage(text: string, sessionId: string): Observable<any> {
    const url = chatbotUrl + 'send/message/' + sessionId;
    const body = {
        text
    };
    console.log(text);
    return this.httpChatBot.post(url, {message: text}, {headers: reqHeader});
  }
}
