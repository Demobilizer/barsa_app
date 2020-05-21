import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ClienteComponentsPage, { ClienteDeleteDialog } from './cliente.page-object';
import ClienteUpdatePage from './cliente-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible
} from '../../util/utils';

const expect = chai.expect;

describe('Cliente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clienteComponentsPage: ClienteComponentsPage;
  let clienteUpdatePage: ClienteUpdatePage;
  let clienteDeleteDialog: ClienteDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load Clientes', async () => {
    await navBarPage.getEntityPage('cliente');
    clienteComponentsPage = new ClienteComponentsPage();
    expect(await clienteComponentsPage.title.getText()).to.match(/Clientes/);

    expect(await clienteComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([clienteComponentsPage.noRecords, clienteComponentsPage.table]);

    beforeRecordsCount = (await isVisible(clienteComponentsPage.noRecords)) ? 0 : await getRecordsCount(clienteComponentsPage.table);
  });

  it('should load create Cliente page', async () => {
    await clienteComponentsPage.createButton.click();
    clienteUpdatePage = new ClienteUpdatePage();
    expect(await clienteUpdatePage.getPageTitle().getAttribute('id')).to.match(/barsaAppApp.cliente.home.createOrEditLabel/);
    await clienteUpdatePage.cancel();
  });

  it('should create and save Clientes', async () => {
    await clienteComponentsPage.createButton.click();
    await clienteUpdatePage.setTipoDocInput('tipoDoc');
    expect(await clienteUpdatePage.getTipoDocInput()).to.match(/tipoDoc/);
    await clienteUpdatePage.setNoIdentificacionInput('5');
    expect(await clienteUpdatePage.getNoIdentificacionInput()).to.eq('5');
    await clienteUpdatePage.setNombreInput('nombre');
    expect(await clienteUpdatePage.getNombreInput()).to.match(/nombre/);
    await clienteUpdatePage.setApellidoInput('apellido');
    expect(await clienteUpdatePage.getApellidoInput()).to.match(/apellido/);
    await clienteUpdatePage.setDireccionInput('direccion');
    expect(await clienteUpdatePage.getDireccionInput()).to.match(/direccion/);
    await clienteUpdatePage.setTelefonoInput('telefono');
    expect(await clienteUpdatePage.getTelefonoInput()).to.match(/telefono/);
    await clienteUpdatePage.setCelularInput('celular');
    expect(await clienteUpdatePage.getCelularInput()).to.match(/celular/);
    await clienteUpdatePage.setEmailInput('email');
    expect(await clienteUpdatePage.getEmailInput()).to.match(/email/);
    await waitUntilDisplayed(clienteUpdatePage.saveButton);
    await clienteUpdatePage.save();
    await waitUntilHidden(clienteUpdatePage.saveButton);
    expect(await isVisible(clienteUpdatePage.saveButton)).to.be.false;

    expect(await clienteComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(clienteComponentsPage.table);

    await waitUntilCount(clienteComponentsPage.records, beforeRecordsCount + 1);
    expect(await clienteComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last Cliente', async () => {
    const deleteButton = clienteComponentsPage.getDeleteButton(clienteComponentsPage.records.last());
    await click(deleteButton);

    clienteDeleteDialog = new ClienteDeleteDialog();
    await waitUntilDisplayed(clienteDeleteDialog.deleteModal);
    expect(await clienteDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/barsaAppApp.cliente.delete.question/);
    await clienteDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(clienteDeleteDialog.deleteModal);

    expect(await isVisible(clienteDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([clienteComponentsPage.noRecords, clienteComponentsPage.table]);

    const afterCount = (await isVisible(clienteComponentsPage.noRecords)) ? 0 : await getRecordsCount(clienteComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
