import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ProductoComponentsPage, { ProductoDeleteDialog } from './producto.page-object';
import ProductoUpdatePage from './producto-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('Producto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productoComponentsPage: ProductoComponentsPage;
  let productoUpdatePage: ProductoUpdatePage;
  let productoDeleteDialog: ProductoDeleteDialog;
  const fileToUpload = '../../../../../../src/main/webapp/content/images/logo-jhipster.png';
  const absolutePath = path.resolve(__dirname, fileToUpload);
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

  it('should load Productos', async () => {
    await navBarPage.getEntityPage('producto');
    productoComponentsPage = new ProductoComponentsPage();
    expect(await productoComponentsPage.title.getText()).to.match(/Productos/);

    expect(await productoComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([productoComponentsPage.noRecords, productoComponentsPage.table]);

    beforeRecordsCount = (await isVisible(productoComponentsPage.noRecords)) ? 0 : await getRecordsCount(productoComponentsPage.table);
  });

  it('should load create Producto page', async () => {
    await productoComponentsPage.createButton.click();
    productoUpdatePage = new ProductoUpdatePage();
    expect(await productoUpdatePage.getPageTitle().getAttribute('id')).to.match(/barsaAppApp.producto.home.createOrEditLabel/);
    await productoUpdatePage.cancel();
  });

  it('should create and save Productos', async () => {
    await productoComponentsPage.createButton.click();
    await productoUpdatePage.setCodigoInput('codigo');
    expect(await productoUpdatePage.getCodigoInput()).to.match(/codigo/);
    await productoUpdatePage.setDescripcionInput('descripcion');
    expect(await productoUpdatePage.getDescripcionInput()).to.match(/descripcion/);
    await productoUpdatePage.setImagenInput(absolutePath);
    await productoUpdatePage.setUnidadInput('unidad');
    expect(await productoUpdatePage.getUnidadInput()).to.match(/unidad/);
    await productoUpdatePage.setCantidadInput('5');
    expect(await productoUpdatePage.getCantidadInput()).to.eq('5');
    await productoUpdatePage.setPrecioInput('5');
    expect(await productoUpdatePage.getPrecioInput()).to.eq('5');
    await productoUpdatePage.setIvaInput('5');
    expect(await productoUpdatePage.getIvaInput()).to.eq('5');
    await productoUpdatePage.setIcovalorInput('5');
    expect(await productoUpdatePage.getIcovalorInput()).to.eq('5');
    await waitUntilDisplayed(productoUpdatePage.saveButton);
    await productoUpdatePage.save();
    await waitUntilHidden(productoUpdatePage.saveButton);
    expect(await isVisible(productoUpdatePage.saveButton)).to.be.false;

    expect(await productoComponentsPage.createButton.isEnabled()).to.be.true;

    await waitUntilDisplayed(productoComponentsPage.table);

    await waitUntilCount(productoComponentsPage.records, beforeRecordsCount + 1);
    expect(await productoComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
  });

  it('should delete last Producto', async () => {
    const deleteButton = productoComponentsPage.getDeleteButton(productoComponentsPage.records.last());
    await click(deleteButton);

    productoDeleteDialog = new ProductoDeleteDialog();
    await waitUntilDisplayed(productoDeleteDialog.deleteModal);
    expect(await productoDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/barsaAppApp.producto.delete.question/);
    await productoDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(productoDeleteDialog.deleteModal);

    expect(await isVisible(productoDeleteDialog.deleteModal)).to.be.false;

    await waitUntilAnyDisplayed([productoComponentsPage.noRecords, productoComponentsPage.table]);

    const afterCount = (await isVisible(productoComponentsPage.noRecords)) ? 0 : await getRecordsCount(productoComponentsPage.table);
    expect(afterCount).to.eq(beforeRecordsCount);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
