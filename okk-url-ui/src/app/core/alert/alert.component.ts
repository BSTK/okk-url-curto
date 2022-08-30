import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent {

  @Input('modalCss') modalCss: string = '';
  @Input('titulo') titulo: string = '';
  @Input('descricao') descricao: string = '';

  @ViewChild('hrefModal') hrefModal?: ElementRef;

}
