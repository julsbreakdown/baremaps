/*
 * Copyright (C) 2020 The Baremaps Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.baremaps.tile.mbtiles;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.baremaps.blob.Blob;
import com.baremaps.tile.Tile;
import com.baremaps.tile.TileStore;
import com.baremaps.tile.TileStoreException;
import java.io.InputStream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public abstract class TileStoreTest {

  @Test
  @Tag("integration")
  void readWriteDeleteTile() throws Exception {
    TileStore tileStore = createTileStore();
    Tile tile = new Tile(1, 2, 3);
    byte[] bytes = "tile_content".getBytes();
    Blob blob = Blob.builder().withByteArray(bytes).build();

    // Write data
    tileStore.write(tile, blob);

    // Read the data
    try (InputStream inputStream = tileStore.read(tile).getInputStream()) {
      assertArrayEquals(bytes, inputStream.readAllBytes());
    }

    // Delete the data
    tileStore.delete(tile);
    assertThrows(TileStoreException.class, () -> tileStore.read(tile));
  }

  public abstract TileStore createTileStore() throws Exception;
}
