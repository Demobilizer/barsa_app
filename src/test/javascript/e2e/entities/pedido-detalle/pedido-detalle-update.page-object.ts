import { element, by, ElementFinder } from 'protractor';

export default class PedidoDetalleUpdatePage {
  pageTitle: ElementFinder = element(by.id('barsaAppApp.pedidoDetalle.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  posicionInput: ElementFinder = element(by.css('input#pedido-detalle-posicion'));
  cantidadInput: ElementFinder = element(by.css('input#pedido-detalle-cantidad'));
  totalInput: ElementFinder = element(by.css('input#pedido-detalle-total'));
  pedidoCabeceraSelect: ElementFinder = element(by.css('select#pedido-detalle-pedidoCabecera'));
  productoSelect: ElementFinder = element(by.css('select#pedido-detalle-producto'));

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

  async pedidoCabeceraSelectLastOption() {
    await this.pedidoCabeceraSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async pedidoCabeceraSelectOption(option) {
    await this.pedidoCabeceraSelect.sendKeys(option);
  }

  getPedidoCabeceraSelect() {
    return this.pedidoCabeceraSelect;
  }

  async getPedidoCabeceraSelectedOption() {
    return this.pedidoCabeceraSelect.element(by.css('option:checked')).getText();
  }

  async productoSelectLastOption() {
    await this.productoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productoSelectOption(option) {
    await this.productoSelect.sendKeys(option);
  }

  getProductoSelect() {
    return this.productoSelect;
  }

  async getProductoSelectedOption() {
    return this.productoSelect.element(by.css('option:checked')).getText();
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
