import { element, by, ElementFinder } from 'protractor';

export default class ListaPreciosUpdatePage {
  pageTitle: ElementFinder = element(by.id('barsaAppApp.listaPrecios.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  descripcionInput: ElementFinder = element(by.css('input#lista-precios-descripcion'));
  porcentajeInput: ElementFinder = element(by.css('input#lista-precios-porcentaje'));
  valorInput: ElementFinder = element(by.css('input#lista-precios-valor'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDescripcionInput(descripcion) {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput() {
    return this.descripcionInput.getAttribute('value');
  }

  async setPorcentajeInput(porcentaje) {
    await this.porcentajeInput.sendKeys(porcentaje);
  }

  async getPorcentajeInput() {
    return this.porcentajeInput.getAttribute('value');
  }

  async setValorInput(valor) {
    await this.valorInput.sendKeys(valor);
  }

  async getValorInput() {
    return this.valorInput.getAttribute('value');
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
