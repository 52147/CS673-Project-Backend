import torch.optim as optim
import time
from pathlib import Path
import os
import torch


class AverageMeter(object):
    """Computes and stores the average and current value"""

    def __init__(self):
        self.val = 0
        self.avg = 0
        self.sum = 0
        self.count = 0
        self.reset()

    def reset(self):
        self.val = 0
        self.avg = 0
        self.sum = 0
        self.count = 0

    def update(self, val, n=1):
        self.val = val
        self.sum += val * n
        self.count += n
        self.avg = self.sum / self.count


def get_batch_label(d, i):
    label = []
    for idx in i:
        label.append(list(d.labels[idx].values())[0])
    return label


def model_info(model):  # Plots a line-by-line description of a PyTorch model
    n_p = sum(x.numel() for x in model.parameters())  # number parameters
    n_g = sum(x.numel() for x in model.parameters() if x.requires_grad)  # number gradients
    print('\n%5s %50s %9s %12s %20s %12s %12s' % ('layer', 'name', 'gradient', 'parameters', 'shape', 'mu', 'sigma'))
    for i, (name, p) in enumerate(model.named_parameters()):
        name = name.replace('module_list.', '')
        print('%5g %50s %9s %12g %20s %12.3g %12.3g' % (
            i, name, p.requires_grad, p.numel(), list(p.shape), p.mean(), p.std()))
    print('Model Summary: %g layers, %g parameters, %g gradients\n' % (i + 1, n_p, n_g))


def get_data_targets(input_label):
    """
    length = 一个batch中的总字符长度
    text = 一个batch中的字符所对应的下标
    Parameters
    ----------
    input_label

    Returns
    -------

    """
    targets = []
    targets_length = []
    for label in input_label:
        targets += label
        targets_length.append(len(label))
    return torch.IntTensor(targets), torch.IntTensor(targets_length)


def get_pred_labels(outputs):
    if isinstance(outputs, torch.Tensor):
        outputs = outputs.cpu().detach().numpy()
    outputs = decode((outputs))
    return outputs


def decode(inputs):
    pred_labels = []
    for output in inputs:
        label = 0
        pred_label = []
        for i in range(len(output)):
            if output[i] != 0 and output[i] != label:
                pred_label.append(output[i])
            label = output[i]
        pred_labels.append(pred_label)
    return pred_labels


def decode_name(pred_labels, class_name):
    pred_names = []
    for preb_label in pred_labels:
        preb_label = [class_name[int(l)] for l in preb_label]
        pred_names.append("".join(preb_label))
    return pred_names


def get_accuracy(pd_preds, gt_preds):
    acc = [pd == gt for pd, gt in zip(pd_preds, gt_preds)]
    acc = sum(acc) / len(acc)
    return acc
