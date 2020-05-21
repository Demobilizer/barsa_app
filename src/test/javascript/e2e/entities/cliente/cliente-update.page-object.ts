import { element, by, ElementFinder } from 'protractor';

export default class ClienteUpdatePage {
  pageTitle: ElementFinder = element(by.id('barsaAppApp.cliente.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  tipoDocInput: ElementFinder = element(by.css('input#cliente-tipoDoc'));
  noIdentificacionInput: ElementFinder = element(by.css('input#cliente-noIdentificacion'));
  nombreInput: ElementFinder = element(by.css('input#cliente-nombre'));
  apellidoInput: ElementFinder = element(by.css('input#cliente-apellido'));
  direccionInput: ElementFinder = element(by.css('input#cliente-direccion'));
  telefonoInput: ElementFinder = element(by.css('input#cliente-telefono'));
  celularInput: ElementFinder = element(by.css('input#cliente-celular'));
  emailInput: ElementFinder = element(by.css('input#cliente-email'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTipoDocInput(tipoDoc) {
    await this.tipoDocInput.sendKeys(tipoDoc);
  }

  async getTipoDocInput() {
    return this.tipoDocInput.getAttribute('value');
  }

  async setNoIdentificacionInput(noIdentificacion) {
    await this.noIdentificacionInput.sendKeys(noIdentificacion);
  }

  async getNoIdentificacionInput() {
    return this.noIdentificacionInput.getAttribute('value');
  }

  async setNombreInput(nombre) {
    await this.nombreInput.sendKeys(nombre);
  }

  async getNombreInput() {
    return this.nombreInput.getAttribute('value');
  }

  async setApellidoInput(apellido) {
    await this.apellidoInput.sendKeys(apellido);
  }

  async getApellidoInput() {
    return this.apellidoInput.getAttribute('value');
  }

  async setDireccionInput(direccion) {
    await this.direccionInput.sendKeys(direccion);
  }

  async getDireccionInput() {
    return this.direccionInput.getAttribute('value');
  }

  async setTelefonoInput(telefono) {
    await this.telefonoInput.sendKeys(telefono);
  }

  async getTelefonoInput() {
    return this.telefonoInput.getAttribute('value');
  }

  async setCelularInput(celular) {
    await this.celularInput.sendKeys(celular);
  }

  async getCelularInput() {
    return this.celularInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return this.emailInput.getAttribute('value');
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
