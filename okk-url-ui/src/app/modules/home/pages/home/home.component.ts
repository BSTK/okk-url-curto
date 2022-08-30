import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  urlOriginal: string = '';
  mostrarUrlEncurtada: boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.resetar();
  }

  encurtar(): void {
    console.log('Encurtando Url original: ', this.urlOriginal);
    this.mostrarUrlEncurtada = true;
  }

  private resetar(): void {
    this.urlOriginal = '';
    this.mostrarUrlEncurtada = false;
  }
}
