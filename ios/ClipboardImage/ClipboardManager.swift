//
//  ClipboardManager.swift
//  StatusIm
//
//  Created by Gheorghe on 03.11.2020.
//  Copyright © 2020 Status. All rights reserved.
//

import Foundation
import UIKit

@objc(MediaClipboard)
class MediaClipboard: NSObject {
  
  @objc(hasImages:resolver:rejecter:)
  func copyImage(_ base64Image: String,
                 resolver resolve: RCTPromiseResolveBlock,
                 rejecter reject: RCTPromiseRejectBlock) -> Void {
    if let data = Data.init(base64Encoded: base64Image, options: .init(rawValue: 0)), let image = UIImage(data: data) {
      UIPasteboard.general.image = image
      resolve(true)
    } else {
      reject("CANT_COPY_IMAGE", "The provided image could not be copied", nil)
    }
  }
  
  @objc(hasImages:rejecter:)
  func hasImages(_ resolve: RCTPromiseResolveBlock,
                 rejecter reject: RCTPromiseRejectBlock) -> Void {
    resolve(UIPasteboard.general.hasImages)
  }
  
  @objc(paste:rejecter:)
  func paste(_ resolve: RCTPromiseResolveBlock,
             rejecter reject: RCTPromiseRejectBlock) -> Void {
    if let stringValue = UIPasteboard.general.string {
       resolve([
        "value": stringValue,
        "type": "text/plain"
      ])
    }
    
    if let url = UIPasteboard.general.url {
      resolve([
        "value": url.absoluteString,
        "type": "text/plain"
      ])
    }
    
    if let image = UIPasteboard.general.image {
      let data = image.jpegData(compressionQuality: 100)
      if let base64 = data?.base64EncodedString() {
        resolve([
          "value": "data:image/jpeg;base64," + base64,
          "type": "image/jpeg"
        ])
      }
    }
    
    resolve([:])
  }
}
