import { element, by, ElementFinder } from 'protractor';

export default class ProductoUpdatePage {
  pageTitle: ElementFinder = element(by.id('barsaAppApp.producto.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  codigoInput: ElementFinder = element(by.css('input#producto-codigo'));
  descripcionInput: ElementFinder = element(by.css('input#producto-descripcion'));
  imagenInput: ElementFinder = element(by.css('input#file_imagen'));
  unidadInput: ElementFinder = element(by.css('input#producto-unidad'));
  cantidadInput: ElementFinder = element(by.css('input#producto-cantidad'));
  precioInput: ElementFinder = element(by.css('input#producto-precio'));
  ivaInput: ElementFinder = element(by.css('input#producto-iva'));
  icovalorInput: ElementFinder = element(by.css('input#producto-icovalor'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCodigoInput(codigo) {
    await this.codigoInput.sendKeys(codigo);
  }

  async getCodigoInput() {
    return this.codigoInput.getAttribute('value');
  }

  async setDescripcionInput(descripcion) {
    await this.descripcionInput.sendKeys(descripcion);
  }

  async getDescripcionInput() {
    return this.descripcionInput.getAttribute('value');
  }

  async setImagenInput(imagen) {
    await this.imagenInput.sendKeys(imagen);
  }

  async getImagenInput() {
    return this.imagenInput.getAttribute('value');
  }

  async setUnidadInput(unidad) {
    await this.unidadInput.sendKeys(unidad);
  }

  async getUnidadInput() {
    return this.unidadInput.getAttribute('value');
  }

  async setCantidadInput(cantidad) {
    await this.cantidadInput.sendKeys(cantidad);
  }

  async getCantidadInput() {
    return this.cantidadInput.getAttribute('value');
  }

  async setPrecioInput(precio) {
    await this.precioInput.sendKeys(precio);
  }

  async getPrecioInput() {
    return this.precioInput.getAttribute('value');
  }

  async setIvaInput(iva) {
    await this.ivaInput.sendKeys(iva);
  }

  async getIvaInput() {
    return this.ivaInput.getAttribute('value');
  }

  async setIcovalorInput(icovalor) {
    await this.icovalorInput.sendKeys(icovalor);
  }

  async getIcovalorInput() {
    return this.icovalorInput.getAttribute('value');
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
