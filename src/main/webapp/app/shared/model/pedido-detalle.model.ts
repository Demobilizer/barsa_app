import { IPedidoCabecera } from 'app/shared/model/pedido-cabecera.model';

export interface IPedidoDetalle {
  id?: number;
  posicion?: number;
  cantidad?: number;
  total?: number;
  pedidoCabecera?: IPedidoCabecera;
}

export const defaultValue: Readonly<IPedidoDetalle> = {};
