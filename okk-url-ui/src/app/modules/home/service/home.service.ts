import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Api} from '../../../app.api';
import {Observable} from 'rxjs';
import {Jogo} from '../pages/home/jogo'

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private readonly httpClient: HttpClient) { }

  public jogos(): Observable<Jogo[]> {
    return this.httpClient.get<Jogo[]>(Api.URLS.plataforma.jogos);
  }
}
