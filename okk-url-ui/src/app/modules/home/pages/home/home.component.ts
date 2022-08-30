import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../service/home.service';
import {UrlResponse} from './url';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  urlOriginal: string = '';
  urlEncurtada: string = '';
  urlQrCode: string = 'https://via.placeholder.com/200x200';

  mostrarUrlEncurtada: boolean = false;

  constructor(private readonly homeService: HomeService) { }

  ngOnInit(): void {
    this.resetar();
  }

  encurtar(): void {
    if (this.urlOriginal && this.urlOriginal != '') {
      this
        .homeService
        .encurtar({
          url: this.urlOriginal
        })
        .subscribe((response: UrlResponse) => {
          if (response) {
            this.mostrarUrlEncurtada = true;
            this.urlQrCode = response.url_qr_code;
            this.urlEncurtada = response.url_encurtada;
          }
        });
    }
  }

  private resetar(): void {
    this.urlOriginal = '';
    this.mostrarUrlEncurtada = false;
    this.urlQrCode = 'https://via.placeholder.com/200x200';
  }
}
