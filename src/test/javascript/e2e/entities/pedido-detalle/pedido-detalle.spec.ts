import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PedidoDetalleComponentsPage, { PedidoDetalleDeleteDialog } from './pedido-detalle.page-object';
import PedidoDetalleUpdatePage from './pedido-detalle-update.page-object';
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

describe('PedidoDetalle e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pedidoDetalleComponentsPage: PedidoDetalleComponentsPage;
  let pedidoDetalleUpdatePage: PedidoDetalleUpdatePage;
  /* let pedidoDetalleDeleteDialog: PedidoDetalleDeleteDialog; */
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

  it('should load PedidoDetalles', async () => {
    await navBarPage.getEntityPage('pedido-detalle');
    pedidoDetalleComponentsPage = new PedidoDetalleComponentsPage();
    expect(await pedidoDetalleComponentsPage.title.getText()).to.match(/Pedido Detalles/);

    expect(await pedidoDetalleComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilAnyDisplayed([pedidoDetalleComponentsPage.noRecords, pedidoDetalleComponentsPage.table]);

    beforeRecordsCount = (await isVisible(pedidoDetalleComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(pedidoDetalleComponentsPage.table);
  });

  it('should load create PedidoDetalle page', async () => {
    await pedidoDetalleComponentsPage.createButton.click();
    pedidoDetalleUpdatePage = new PedidoDetalleUpdatePage();
    expect(await pedidoDetalleUpdatePage.getPageTitle().getAttribute('id')).to.match(/barsaAppApp.pedidoDetalle.home.createOrEditLabel/);
    await pedidoDetalleUpdatePage.cancel();
  });

  /*  it('should create and save PedidoDetalles', async () => {
        await pedidoDetalleComponentsPage.createButton.click();
        await pedidoDetalleUpdatePage.setPosicionInput('5');
        expect(await pedidoDetalleUpdatePage.getPosicionInput()).to.eq('5');
        await pedidoDetalleUpdatePage.setCantidadInput('5');
        expect(await pedidoDetalleUpdatePage.getCantidadInput()).to.eq('5');
        await pedidoDetalleUpdatePage.setTotalInput('5');
        expect(await pedidoDetalleUpdatePage.getTotalInput()).to.eq('5');
        await pedidoDetalleUpdatePage.pedidoCabeceraSelectLastOption();
        await pedidoDetalleUpdatePage.productoSelectLastOption();
        await waitUntilDisplayed(pedidoDetalleUpdatePage.saveButton);
        await pedidoDetalleUpdatePage.save();
        await waitUntilHidden(pedidoDetalleUpdatePage.saveButton);
        expect(await isVisible(pedidoDetalleUpdatePage.saveButton)).to.be.false;

        expect(await pedidoDetalleComponentsPage.createButton.isEnabled()).to.be.true;

        await waitUntilDisplayed(pedidoDetalleComponentsPage.table);

        await waitUntilCount(pedidoDetalleComponentsPage.records, beforeRecordsCount + 1);
        expect(await pedidoDetalleComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
    }); */

  /*  it('should delete last PedidoDetalle', async () => {

        const deleteButton = pedidoDetalleComponentsPage.getDeleteButton(pedidoDetalleComponentsPage.records.last());
        await click(deleteButton);

        pedidoDetalleDeleteDialog = new PedidoDetalleDeleteDialog();
        await waitUntilDisplayed(pedidoDetalleDeleteDialog.deleteModal);
        expect(await pedidoDetalleDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/barsaAppApp.pedidoDetalle.delete.question/);
        await pedidoDetalleDeleteDialog.clickOnConfirmButton();

        await waitUntilHidden(pedidoDetalleDeleteDialog.deleteModal);

        expect(await isVisible(pedidoDetalleDeleteDialog.deleteModal)).to.be.false;

        await waitUntilAnyDisplayed([pedidoDetalleComponentsPage.noRecords,
        pedidoDetalleComponentsPage.table]);
    
        const afterCount = await isVisible(pedidoDetalleComponentsPage.noRecords) ? 0 : await getRecordsCount(pedidoDetalleComponentsPage.table);
        expect(afterCount).to.eq(beforeRecordsCount);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
