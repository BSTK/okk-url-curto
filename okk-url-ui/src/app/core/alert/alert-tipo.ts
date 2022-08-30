export type AlertTipo = {
  modalCss: string;
  titulo: string;
  descricao: string;
}

export const alertGanhou: AlertTipo = {
  modalCss: 'modal-sucesso',
  titulo: '(-: Parabéns!!',
  descricao: ''
}

export const alertPerdeu: AlertTipo = {
  modalCss: 'modal-error',
  titulo: ')-: PerdeuuU!!',
  descricao: ''
}
