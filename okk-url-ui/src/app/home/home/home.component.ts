import {Component, OnInit} from '@angular/core';
import {UrlCurtoService} from '../service/url-curto.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private readonly urlCurtoService: UrlCurtoService) { }

  ngOnInit(): void { }

}
