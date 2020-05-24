import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';

export interface IProducto {
  id?: number;
  codigo?: string;
  descripcion?: string;
  imagenContentType?: string;
  imagen?: any;
  unidad?: string;
  cantidad?: number;
  precio?: number;
  iva?: number;
  icovalor?: number;
  pedidoDetalles?: IPedidoDetalle[];
}

export const defaultValue: Readonly<IProducto> = {};
