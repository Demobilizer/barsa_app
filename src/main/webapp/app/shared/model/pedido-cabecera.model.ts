import { Moment } from 'moment';
import { IPedidoDetalle } from 'app/shared/model/pedido-detalle.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IPedidoCabecera {
  id?: number;
  fechaCreacion?: Moment;
  fechaEntrega?: Moment;
  pedidoNumero?: number;
  descripcion?: string;
  facturado?: boolean;
  entregado?: boolean;
  totalBruto?: number;
  totalIva?: number;
  totalImpConsumo?: number;
  total?: number;
  pedidoDetalles?: IPedidoDetalle[];
  clienteNo?: ICliente;
}

export const defaultValue: Readonly<IPedidoCabecera> = {
  facturado: false,
  entregado: false
};
