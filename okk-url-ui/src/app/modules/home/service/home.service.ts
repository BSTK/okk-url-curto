import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Api} from '../../../app.api';
import {Observable} from 'rxjs';
import {UrlRequest, UrlResponse} from '../pages/home/url';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private readonly httpClient: HttpClient) { }

  public encurtar(request: UrlRequest): Observable<UrlResponse> {
    return this.httpClient.post<UrlResponse>(Api.URLS.url.encurtar, request);
  }
}
