import {Observable} from 'rxjs';
import {Api} from '../../app.api';
import {Injectable} from '@angular/core';
import {UrlRequest, UrlResponse} from './url';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UrlCurtoService {

  constructor(private readonly httpClient: HttpClient) { }

  public encurtar(request: UrlRequest): Observable<UrlResponse> {
    return this.httpClient.post<UrlResponse>(Api.URLS.urlcurto.encurtar, request);
  }
}
