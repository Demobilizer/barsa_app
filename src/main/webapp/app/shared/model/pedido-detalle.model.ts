import { IPedidoCabecera } from 'app/shared/model/pedido-cabecera.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IPedidoDetalle {
  id?: number;
  posicion?: number;
  cantidad?: number;
  total?: number;
  pedidoCabecera?: IPedidoCabecera;
  producto?: IProducto;
}

export const defaultValue: Readonly<IPedidoDetalle> = {};
