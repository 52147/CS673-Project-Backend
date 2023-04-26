import torch.nn as nn
import torch
import torch.nn.functional as F


class PlateNet(nn.Module):
    def __init__(self, cfg=[32, 32, 64, 64, 'M1', 128, 128, 'M1', 196, 196, 'M0', 256, 256], num_classes=78,
                 is_train=False):
        super(PlateNet, self).__init__()
        self.feature = self.make_layers(cfg, True)
        self.is_train = is_train
        self.loc = nn.MaxPool2d((5, 2), (1, 1), (0, 1), ceil_mode=False)
        self.newCnn = nn.Conv2d(cfg[-1], num_classes, 1, 1)

    def make_layers(self, cfg, batch_norm=False):
        layers = []
        in_channels = 3
        for i in range(len(cfg)):
            if i == 0:
                conv2d = nn.Conv2d(in_channels, cfg[i], kernel_size=5, stride=1)
                if batch_norm:
                    layers += [conv2d, nn.BatchNorm2d(cfg[i]), nn.ReLU(inplace=True)]
                else:
                    layers += [conv2d, nn.ReLU(inplace=True)]
                in_channels = cfg[i]
            else:
                if isinstance(cfg[i], str) and cfg[i][0] == "M":
                    padding = int(cfg[i][1])
                    # layers += [nn.MaxPool2d(kernel_size=3, stride=2, ceil_mode=True)]
                    layers += [nn.MaxPool2d(kernel_size=3, stride=2, ceil_mode=False, padding=padding)]
                    # layers += [nn.MaxPool2d(kernel_size=3, stride=2, ceil_mode=False)]
                else:
                    conv2d = nn.Conv2d(in_channels, cfg[i], kernel_size=3, padding=(1, 1), stride=1)
                    if batch_norm:
                        layers += [conv2d, nn.BatchNorm2d(cfg[i]), nn.ReLU(inplace=True)]
                    else:
                        layers += [conv2d, nn.ReLU(inplace=True)]
                    in_channels = cfg[i]
        return nn.Sequential(*layers)

    def forward(self, x):
        x = self.feature(x)
        x = self.loc(x)
        x = self.newCnn(x)
        if self.is_train:
            b, c, h, w = x.size()
            assert h == 1, "the height of conv must be 1"
            x = x.squeeze(2)  # b *512 * width
            x = x.permute(2, 0, 1)  # [w, b, c]
            output = F.log_softmax(x, dim=2)
            # output = torch.softmax(x, dim=2)
        else:
            x = x.squeeze(2)  # torch.Size([1, 78, 21])
            output = x.transpose(2, 1)  # torch.Size([1, 21, 78])
        return output


if __name__ == "__main__":
    """
    inputs.shape :torch.Size([2, 3, 48, 168])
    outputs.shape:torch.Size([21, 2, 78])
    """
    from basetrainer.utils import torch_tools

    # device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    device = "cpu"
    batch_size = 2
    num_classes = 78
    input_size = [168, 48]
    inputs = torch.randn(size=(batch_size, 3, input_size[1], input_size[0])).to(device)
    cfg = [32, 32, 64, 64, 'M1', 128, 128, 'M1', 196, 196, 'M0', 256, 256]
    model = PlateNet(num_classes=num_classes, cfg=cfg, is_train=False)
    outputs = model(inputs)
    print("inputs.shape :{}".format(inputs.shape))
    print("outputs.shape:{}".format(outputs.shape))
