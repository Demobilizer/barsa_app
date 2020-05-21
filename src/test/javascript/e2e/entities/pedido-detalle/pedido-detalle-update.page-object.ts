import { element, by, ElementFinder } from 'protractor';

export default class PedidoDetalleUpdatePage {
  pageTitle: ElementFinder = element(by.id('barsaAppApp.pedidoDetalle.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  posicionInput: ElementFinder = element(by.css('input#pedido-detalle-posicion'));
  cantidadInput: ElementFinder = element(by.css('input#pedido-detalle-cantidad'));
  totalInput: ElementFinder = element(by.css('input#pedido-detalle-total'));
  pedidoNumeroSelect: ElementFinder = element(by.css('select#pedido-detalle-pedidoNumero'));
  articuloCodigoSelect: ElementFinder = element(by.css('select#pedido-detalle-articuloCodigo'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setPosicionInput(posicion) {
    await this.posicionInput.sendKeys(posicion);
  }

  async getPosicionInput() {
    return this.posicionInput.getAttribute('value');
  }

  async setCantidadInput(cantidad) {
    await this.cantidadInput.sendKeys(cantidad);
  }

  async getCantidadInput() {
    return this.cantidadInput.getAttribute('value');
  }

  async setTotalInput(total) {
    await this.totalInput.sendKeys(total);
  }

  async getTotalInput() {
    return this.totalInput.getAttribute('value');
  }

  async pedidoNumeroSelectLastOption() {
    await this.pedidoNumeroSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async pedidoNumeroSelectOption(option) {
    await this.pedidoNumeroSelect.sendKeys(option);
  }

  getPedidoNumeroSelect() {
    return this.pedidoNumeroSelect;
  }

  async getPedidoNumeroSelectedOption() {
    return this.pedidoNumeroSelect.element(by.css('option:checked')).getText();
  }

  async articuloCodigoSelectLastOption() {
    await this.articuloCodigoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async articuloCodigoSelectOption(option) {
    await this.articuloCodigoSelect.sendKeys(option);
  }

  getArticuloCodigoSelect() {
    return this.articuloCodigoSelect;
  }

  async getArticuloCodigoSelectedOption() {
    return this.articuloCodigoSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
