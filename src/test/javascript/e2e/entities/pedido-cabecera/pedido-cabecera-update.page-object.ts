import { element, by, ElementFinder } from 'protractor';

export default class PedidoCabeceraUpdatePage {
  pageTitle: ElementFinder = element(by.id('barsaAppApp.pedidoCabecera.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  fechaCreacionInput: ElementFinder = element(by.css('input#pedido-cabecera-fechaCreacion'));
  fechaEntregaInput: ElementFinder = element(by.css('input#pedido-cabecera-fechaEntrega'));
  pedidoNumeroInput: ElementFinder = element(by.css('input#pedido-cabecera-pedidoNumero'));
  descripcionInput: ElementFinder = element(by.css('input#pedido-cabecera-descripcion'));
  facturadoInput: ElementFinder = element(by.css('input#pedido-cabecera-facturado'));
  entregadoInput: ElementFinder = element(by.css('input#pedido-cabecera-entregado'));
  totalBrutoInput: ElementFinder = element(by.css('input#pedido-cabecera-totalBruto'));
  totalIvaInput: ElementFinder = element(by.css('input#pedido-cabecera-totalIva'));
  totalImpConsumoInput: ElementFinder = element(by.css('input#pedido-cabecera-totalImpConsumo'));
  totalInput: ElementFinder = element(by.css('input#pedido-cabecera-total'));
  clienteSelect: ElementFinder = element(by.css('select#pedido-cabecera-cliente'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setFechaCreacionInput(fechaCreacion) {
    await this.fechaCreacionInput.sendKeys(fechaCreacion);
  }

  async getFechaCreacionInput() {
    return this.fechaCreacionInput.getAttribute('value');
  }

  async setFechaEntregaInput(fechaEntrega) {
    await this.fechaEntregaInput.sendKeys(fechaEntrega);
  }

  async getFechaEntregaInput() {
    return this.fechaEntregaInput.getAttribute('value');
  }

  async setPedidoNumeroInput(pedidoNumero) {
    await this.pedidoNumeroInput.sendKeys(pedidoNumero);
  }

  async getPedidoNumeroInput() {
    return this.pedidoNumeroInput.getAttribute('value');
  }

  async setDescripcionInput(descripcion) {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput() {
    return this.descripcionInput.getAttribute('value');
  }

  getFacturadoInput() {
    return this.facturadoInput;
  }
  getEntregadoInput() {
    return this.entregadoInput;
  }
  async setTotalBrutoInput(totalBruto) {
    await this.totalBrutoInput.sendKeys(totalBruto);
  }

  async getTotalBrutoInput() {
    return this.totalBrutoInput.getAttribute('value');
  }

  async setTotalIvaInput(totalIva) {
    await this.totalIvaInput.sendKeys(totalIva);
  }

  async getTotalIvaInput() {
    return this.totalIvaInput.getAttribute('value');
  }

  async setTotalImpConsumoInput(totalImpConsumo) {
    await this.totalImpConsumoInput.sendKeys(totalImpConsumo);
  }

  async getTotalImpConsumoInput() {
    return this.totalImpConsumoInput.getAttribute('value');
  }

  async setTotalInput(total) {
    await this.totalInput.sendKeys(total);
  }

  async getTotalInput() {
    return this.totalInput.getAttribute('value');
  }

  async clienteSelectLastOption() {
    await this.clienteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async clienteSelectOption(option) {
    await this.clienteSelect.sendKeys(option);
  }

  getClienteSelect() {
    return this.clienteSelect;
  }

  async getClienteSelectedOption() {
    return this.clienteSelect.element(by.css('option:checked')).getText();
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
